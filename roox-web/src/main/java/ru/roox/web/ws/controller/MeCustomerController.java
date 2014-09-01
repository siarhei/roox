package ru.roox.web.ws.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.roox.domain.AvatarInfo;
import ru.roox.domain.PartnerMapping;
import ru.roox.web.constants.RooxHttpConstants;
import ru.roox.web.ws.errors.EntityNotFoundException;
import ru.roox.web.ws.errors.ForbiddenActionException;
import ru.roox.web.ws.model.CustomerDto;
import ru.roox.web.ws.model.PartnerMappingDto;
import ru.roox.web.ws.model.Response;
import ru.roox.web.ws.model.SuccessResponse;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.List;

/**
 * REST Controller for Customer
 *
 * @author Siarhei Shchahratsou <s.siarhei@gmail.com>
 * @since 01.09.2014
 */
@Controller
public class MeCustomerController extends AbstractController {
    private  final Logger logger = LoggerFactory.getLogger(this.getClass());

    @RequestMapping(value = "/ws/customers/@me", method = RequestMethod.GET)
    public @ResponseBody
    CustomerDto getAuthorizedCustomer(@RequestParam(RooxHttpConstants.REQUEST_PARAM_CUSTOMER_ID) Long customerId) {
        logger.info("Tries to get authorized customer", customerId);

        return customerAssembler.assembly(getCustomerById(customerId));
    }

    @RequestMapping(value = "/ws/customers/@me/partners", method = RequestMethod.GET)
    public @ResponseBody
    List<PartnerMappingDto> getAuthorizedPartners(@RequestParam(RooxHttpConstants.REQUEST_PARAM_CUSTOMER_ID) Long customerId) {
        logger.info("Tries to get partners for authorized customer = {}", customerId);

        return getPartnersForCustomer(customerId);
    }

    @RequestMapping(value = "/ws/customers/@me/partners/{pid}", method = RequestMethod.GET)
    public @ResponseBody
    PartnerMappingDto getAuthorizedPartner(@RequestParam(RooxHttpConstants.REQUEST_PARAM_CUSTOMER_ID) Long customerId,
                                           @PathVariable("pid") Long partnerId) {
        logger.info("Tries to get partner = {} for authorized customer = {}", partnerId, customerId);

        return getPartnerByCustomer(customerId, partnerId);
    }

    @RequestMapping(value = "/ws/customers/@me", method = RequestMethod.DELETE)
    public @ResponseBody
    void deleteAuthCustomer(@RequestParam(RooxHttpConstants.REQUEST_PARAM_CUSTOMER_ID) Long authCustomerId) {
        logger.error("Impossible to delete auth customer = {}", authCustomerId);
        throw new ForbiddenActionException("Impossible to delete customer");
    }

    @RequestMapping(value = "/ws/customers/@me/partners", method = RequestMethod.DELETE)
    public @ResponseBody
    Response deleteAuthPartners(@RequestParam(RooxHttpConstants.REQUEST_PARAM_CUSTOMER_ID) Long authCustomerId) {
        logger.info("Tries to delete all partners for auth customer = {}", authCustomerId);
        customerService.deleteAllPartners(authCustomerId);

        return SuccessResponse.getInstance();
    }

    @RequestMapping(value = "/ws/customers/@me/partners/{pid}", method = RequestMethod.DELETE)
    public @ResponseBody
    Response deleteAuthPartner(@RequestParam(RooxHttpConstants.REQUEST_PARAM_CUSTOMER_ID) Long authCustomerId,
                           @PathVariable("pid") Long partnerId) {
        logger.info("Tries to delete partner = {} for auth customer = {}", partnerId, authCustomerId);
        customerService.deletePartner(authCustomerId, partnerId);

        return SuccessResponse.getInstance();
    }

    @RequestMapping(value = "/ws/customers/@me/partners", method = RequestMethod.POST)
    public @ResponseBody
    PartnerMappingDto createPartnerForAuthCustomer(
            @RequestParam(RooxHttpConstants.REQUEST_PARAM_CUSTOMER_ID) Long authCustomerId,
            @RequestBody PartnerMappingDto dto) {
        logger.info("Tries to create new partner for auth customer = {}", authCustomerId);
        return  partnerMappingAssembler.assembly(
                customerService.createFor(authCustomerId,
                        partnerMappingAssembler.assembly(dto)));
    }

    @RequestMapping(value = "/ws/customers/@me", method = RequestMethod.PUT)
    public @ResponseBody
    PartnerMappingDto updateAuthCustomer() {
        String errorMsg = "Impossible to update auth customer";

        logger.error(errorMsg);
        throw new ForbiddenActionException(errorMsg);
    }

    @RequestMapping(value = "/ws/customers/@me/partners/{pid}", method = RequestMethod.PUT)
    public @ResponseBody
    PartnerMappingDto updatePartnerForAuthCustomer(
            @PathVariable("pid") Long partnerId,
            @RequestParam(RooxHttpConstants.REQUEST_PARAM_CUSTOMER_ID) Long authCustomerId,
            @RequestBody PartnerMappingDto dto) {
        logger.info("Tries to update partner for auth customer = {}", authCustomerId);

        PartnerMapping partnerMapping = customerService.findBy(authCustomerId, partnerId);
        if (partnerMapping == null) {
            logger.error("Partner not found");
            throw new EntityNotFoundException(String.format("Partner mapping not found (id=%d) for customer (id=%d)", partnerId, authCustomerId));
        }
        partnerMappingAssembler.updateEntity(partnerMapping, dto);

        return  partnerMappingAssembler.assembly(customerService.update(partnerMapping));
    }

    @RequestMapping(value = "/ws/customers/@me/partners/{pid}/avatar", method = RequestMethod.POST)
    public @ResponseBody
    PartnerMappingDto uploadPartnerAvatarForAuthCustomer(
            @PathVariable("pid") Long partnerId,
            @RequestParam(RooxHttpConstants.REQUEST_PARAM_CUSTOMER_ID) Long authCustomerId,
            @RequestParam MultipartFile file) throws IOException {
        logger.info("Tries to upload partner's avatar for auth customer = {}", authCustomerId);

        PartnerMapping partnerMapping = customerService.findBy(authCustomerId, partnerId);
        if (partnerMapping == null) {
            logger.error("Partner not found");
            throw new EntityNotFoundException(String.format("Partner mapping not found (id=%d) for customer (id=%d)", partnerId, authCustomerId));
        }
        AvatarInfo avatarInfo = partnerMapping.getAvatar();
        byte avatarData[] = FileCopyUtils.copyToByteArray(file.getInputStream());
        avatarInfo.setData(avatarData);
        avatarInfo.setName(file.getOriginalFilename());

        return  partnerMappingAssembler.assembly(customerService.update(partnerMapping));
    }

    @RequestMapping(value = "/ws/customers/@me/partners/{pid}/avatar", method = RequestMethod.GET)
    public @ResponseBody
    BufferedImage getPartnerAvatarForAuthCustomer(
            @PathVariable("pid") Long partnerId,
            @RequestParam(RooxHttpConstants.REQUEST_PARAM_CUSTOMER_ID) Long authCustomerId) throws IOException {
        logger.info("Tries to get partner's avatar for auth customer = {}", authCustomerId);

        PartnerMapping partnerMapping = customerService.findBy(authCustomerId, partnerId);
        if (partnerMapping == null) {
            logger.error("Partner not found");
            throw new EntityNotFoundException(String.format("Partner mapping not found (id=%d) for customer (id=%d)", partnerId, authCustomerId));
        }

        AvatarInfo avatarInfo = partnerMapping.getAvatar();
        return ImageIO.read(new ByteArrayInputStream(avatarInfo.getData()));
    }
}
