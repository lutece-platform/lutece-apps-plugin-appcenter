package fr.paris.lutece.plugins.appcenter.modules.sources.service;

import java.util.Locale;

import fr.paris.lutece.portal.service.i18n.I18nService;
import fr.paris.lutece.util.ReferenceList;

public class SourcesUtil {
	
	public static final String MARK_REPOSITORY_TYPES = "repository_types";
	public static final String MARK_REPOSITORY_TYPES_MAP = "repository_types_map";
	   
	private static final String[] tabRepositoryTypes= {"site","theme","plugin","module","library"};
	private static  String I18n_REPOSITORY_TYPE_PREFIX="module.appcenter.sources.repositoryTypes.label.";
	private static String EMPTY_I18n_KEY="empty";
	
	
	public static ReferenceList getAllRepositoryType(Locale locale)
	{
		
		return getAllRepositoryType(locale,true);
	}
	public static ReferenceList getAllRepositoryType(Locale locale,boolean bWithEmptyFile)
	{
		
		
		ReferenceList referenceList=new ReferenceList();
		if(bWithEmptyFile)
		{
			referenceList.addItem("", I18nService.getLocalizedString(getReferentielTypeI18nKey(EMPTY_I18n_KEY), locale));
		}
		
		for (int i = 0; i < tabRepositoryTypes.length; i++) {
			referenceList.addItem(tabRepositoryTypes[i],I18nService.getLocalizedString(getReferentielTypeI18nKey(tabRepositoryTypes[i]), locale));
		}
		
		return referenceList;
	
	}
	
	
	public static String getReferentielTypeI18nKey(String strRefCode)
	{
		
		return I18n_REPOSITORY_TYPE_PREFIX+strRefCode;
		
	}
	

}
