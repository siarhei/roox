INSERT INTO PUBLIC.CUSTOMER(ID, FIRSTNAME, MIDNAME, LASTNAME, STATUS, LOGIN, PWDHASH)
  VALUES (1, 'Siarhei', 'Fedaravich', 'Shchahratsou', 'active', 'ssiarhei', 'hkdsgifsoifyqiqgbfvpiwygv33v7');

INSERT INTO PUBLIC.PARTNERMAPPING(ID, PARTNERID, CUSTOMERPARTNERID, FIRSTNAME, MIDNAME, LASTNAME, NAME, CUSTOMER_ID)
VALUES
  (2, 'facebook_ref_id', 'facebook_ssiarhei_ref_id', 'FB_Siarhei', 'FB_Fedaravich', 'FB_Shchahratsou', 'FB_avatar', 1),
  (3, 'vk_ref_id', 'vk_ssiarhei_ref_id', 'VK_Siarhei', 'VK_Fedaravich', 'VK_Shchahratsou', 'VK_avatar', 1)
;