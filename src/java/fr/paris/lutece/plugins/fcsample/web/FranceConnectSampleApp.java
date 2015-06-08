/*
 * Copyright (c) 2002-2015, Mairie de Paris
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 *
 *  1. Redistributions of source code must retain the above copyright notice
 *     and the following disclaimer.
 *
 *  2. Redistributions in binary form must reproduce the above copyright notice
 *     and the following disclaimer in the documentation and/or other materials
 *     provided with the distribution.
 *
 *  3. Neither the name of 'Mairie de Paris' nor 'Lutece' nor the names of its
 *     contributors may be used to endorse or promote products derived from
 *     this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDERS OR CONTRIBUTORS BE
 * LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 * CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
 * POSSIBILITY OF SUCH DAMAGE.
 *
 * License 1.0
 */
package fr.paris.lutece.plugins.fcsample.web;

import fr.paris.lutece.portal.service.security.LuteceUser;
import fr.paris.lutece.portal.service.security.SecurityService;
import fr.paris.lutece.portal.service.security.UserNotSignedException;
import fr.paris.lutece.portal.web.xpages.XPage;
import fr.paris.lutece.portal.util.mvc.xpage.MVCApplication;
import fr.paris.lutece.portal.util.mvc.commons.annotations.View;
import fr.paris.lutece.portal.util.mvc.xpage.annotations.Controller;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

/**
 * This class provides a simple implementation of an XPage
 */
 
@Controller( xpageName = "fcsample" , pageTitleI18nKey = "fcsample.xpage.fcsample.pageTitle" , pagePathI18nKey = "fcsample.xpage.fcsample.pagePathLabel" )
public class FranceConnectSampleApp extends MVCApplication
{
    private static final String TEMPLATE_XPAGE = "/skin/plugins/fcsample/home.html";
    private static final String TEMPLATE_DEMARCHE_FORM = "/skin/plugins/fcsample/demarche-form.html";
    private static final String VIEW_HOME = "home";
    private static final String VIEW_DEMARCHE_FORM = "demarcheForm";
    private static final String MARK_LASTNAME = "lastname";
    private static final String MARK_FIRSTNAME = "firstname";
    
    private static final long serialVersionUID = 1L;
    
    /**
     * Returns the content of the page fcsample. 
     * @param request The HTTP request
     * @return The view
     */
    @View( value = VIEW_HOME , defaultView = true )
    public XPage viewHome( HttpServletRequest request )
    {
        return getXPage( TEMPLATE_XPAGE, request.getLocale(  ) );
    }
    
    /**
     * Returns the content of the page fcsample. 
     * @param request The HTTP request
     * @return The view
     */
    @View( value = VIEW_DEMARCHE_FORM )
    public XPage viewDemarcheForm( HttpServletRequest request ) throws UserNotSignedException
    {
        LuteceUser user = SecurityService.getInstance().getRegisteredUser(request);
        
        if( user == null )
        {
            throw new UserNotSignedException();
        }
        
        Map<String, Object> model = getModel();
        model.put( MARK_FIRSTNAME , user.getUserInfo( LuteceUser.NAME_GIVEN ) );
        model.put( MARK_LASTNAME , user.getUserInfo( LuteceUser.NAME_FAMILY ) );
        return getXPage( TEMPLATE_DEMARCHE_FORM, request.getLocale(  ) , model );
    }
    
    
    
}
