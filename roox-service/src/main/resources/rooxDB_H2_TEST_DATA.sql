INSERT INTO PUBLIC.CUSTOMER(ID, FIRSTNAME, MIDNAME, LASTNAME, STATUS, LOGIN, PWDHASH)
  VALUES (1, 'crmFirstName', 'crmMidName', 'crmLastName', 'active', 'crmLogin', 's87tq8vt8tvqy2vb12vb1vbvm');

INSERT INTO PUBLIC.PARTNERMAPPING(ID, PARTNERID, CUSTOMERPARTNERID, FIRSTNAME, MIDNAME, LASTNAME, NAME, CUSTOMER_ID)
VALUES
  (2, 'fb_ref_id', 'fb_crm_ref_id', 'fb_crmFirstName', 'fb_crmMidName', 'fb_crmLastName', 'fb_crmAvatar', 1),
  (3, 'vk_ref_id', 'vk_crm_ref_id', 'vk_crmFirstName', 'vk_crmMidName', 'vk_crmLastName', 'vk_crmAvatar', 1)
;