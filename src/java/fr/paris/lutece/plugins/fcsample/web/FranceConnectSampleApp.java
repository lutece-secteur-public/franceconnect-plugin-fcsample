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

import fr.paris.lutece.plugins.fcsample.business.FormData;
import fr.paris.lutece.plugins.fcsample.dataclient.RevenuDataClient;
import fr.paris.lutece.plugins.fcsample.dataclient.UserDataClient;
import fr.paris.lutece.plugins.fcsample.dataclient.UserRevenu;
import fr.paris.lutece.plugins.fcsample.service.RedirectUtils;
import fr.paris.lutece.plugins.franceconnect.oidc.UserInfo;
import fr.paris.lutece.plugins.franceconnect.service.DataClientService;
import fr.paris.lutece.portal.util.mvc.commons.annotations.Action;
import fr.paris.lutece.portal.util.mvc.commons.annotations.View;
import fr.paris.lutece.portal.util.mvc.xpage.MVCApplication;
import fr.paris.lutece.portal.util.mvc.xpage.annotations.Controller;
import fr.paris.lutece.portal.web.xpages.XPage;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;


/**
 * This class provides a simple implementation of an XPage
 */
@Controller( xpageName = "fcsample", pageTitleI18nKey = "fcsample.xpage.fcsample.pageTitle", pagePathI18nKey = "fcsample.xpage.fcsample.pagePathLabel" )
public class FranceConnectSampleApp extends MVCApplication
{
    // VIEWS
    public static final String VIEW_HOME = "home";
    public static final String VIEW_DEMARCHE_FORM = "demarcheForm";
    public static final String VIEW_DEMARCHE_ETAPE2 = "demarcheEtape2";
    public static final String VIEW_DEMARCHE_FIN = "demarcheFin";

    // ACTIONS
    public static final String ACTION_START_DEMARCHE = "startDemarche";
    public static final String ACTION_PROCESS_FORM = "processForm";

    // TEMPLATES
    private static final String TEMPLATE_XPAGE = "/skin/plugins/fcsample/home.html";
    private static final String TEMPLATE_DEMARCHE_FORM = "/skin/plugins/fcsample/demarche-form.html";
    private static final String TEMPLATE_DEMARCHE_ETAPE2 = "/skin/plugins/fcsample/demarche-etape2.html";
    private static final String TEMPLATE_DEMARCHE_FIN = "/skin/plugins/fcsample/demarche-etape-fin.html";
    private static final String MARK_LASTNAME = "lastname";
    private static final String MARK_FIRSTNAME = "firstname";
    private static final String MARK_FORM_DATA = "form";
    private static final String MARK_MONTANT = "montant";
    private static final String DATACLIENT_USER = "user";
    private static final String DATACLIENT_REVENU = "revenu";
    private static final long serialVersionUID = 1L;
    private UserInfo _userInfo;
    private UserRevenu _userRevenu;
    private FormData _formData;

    /**
     * Returns the content of the page fcsample.
     * @param request The HTTP request
     * @return The view
     */
    @View( value = VIEW_HOME, defaultView = true )
    public XPage viewHome( HttpServletRequest request )
    {
        return getXPage( TEMPLATE_XPAGE, request.getLocale(  ) );
    }

    /**
     * Start the process by connecting to FranceConnect
     * @param request The HTTP request
     * @return Redirection
     */
    @Action( ACTION_START_DEMARCHE )
    public XPage startDemarche( HttpServletRequest request )
    {
        _userInfo = (UserInfo) request.getSession(  ).getAttribute( UserDataClient.ATTRIBUTE_USERINFO );

        if ( _userInfo == null )
        {
            String strUrl = DataClientService.instance(  ).getDataClientUrl( DATACLIENT_USER );

            return redirect( request, strUrl );
        }

        return redirectView( request, VIEW_DEMARCHE_FORM );
    }

    /**
     * Returns the content of the page fcsample.
     * @param request The HTTP request
     * @return The view
     */
    @View( VIEW_DEMARCHE_FORM )
    public XPage viewDemarcheForm( HttpServletRequest request )
    {
        _userInfo = (UserInfo) request.getSession(  ).getAttribute( UserDataClient.ATTRIBUTE_USERINFO );

        if ( _userInfo == null )
        {
            return redirect( request, RedirectUtils.getActionUrl( request, ACTION_START_DEMARCHE ) );
        }

        Map<String, Object> model = getModel(  );
        model.put( MARK_FIRSTNAME, _userInfo.getGivenName(  ) );
        model.put( MARK_LASTNAME, _userInfo.getFamilyName(  ) );

        return getXPage( TEMPLATE_DEMARCHE_FORM, request.getLocale(  ), model );
    }

    /**
     * Process the form
     * @param request The HTTP request
     * @return Redirection
     */
    @Action( ACTION_PROCESS_FORM )
    public XPage processForm( HttpServletRequest request )
    {
        _formData = new FormData(  );
        populate( _formData, request );

        if ( !validateBean( _formData ) )
        {
            // TODO Process error
        }

        System.out.println( "Données du formulaire : " + _formData.getPrenomEnfant(  ) + " " +
            _formData.getNomEnfant(  ) );

        return redirect( request, DataClientService.instance(  ).getDataClientUrl( DATACLIENT_REVENU ) );
    }

    /**
     * Display the view Etape2
     * @param request The HTTP request
     * @return The XPage
     */
    @View( VIEW_DEMARCHE_ETAPE2 )
    public XPage viewDemarcheEtape2( HttpServletRequest request )
    {
        Map<String, Object> model = getModel(  );
        model.put( MARK_FORM_DATA, _formData );
        _userRevenu = (UserRevenu) request.getSession(  ).getAttribute( RevenuDataClient.ATTRIBUTE_USERREVENU );
        model.put( MARK_MONTANT, _userRevenu.getRFR() );

        return getXPage( TEMPLATE_DEMARCHE_ETAPE2, request.getLocale(  ), model );
    }

    /**
     * Display the view Etape FIN
     * @param request The HTTP request
     * @return The XPage
     */
    @View( VIEW_DEMARCHE_FIN )
    public XPage viewDemarcheFin( HttpServletRequest request )
    {
        return getXPage( TEMPLATE_DEMARCHE_FIN, request.getLocale(  ) );
    }
}
