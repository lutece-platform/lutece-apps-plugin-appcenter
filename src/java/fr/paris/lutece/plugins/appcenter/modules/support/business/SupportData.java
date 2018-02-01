
package fr.paris.lutece.plugins.appcenter.modules.support.business;

import fr.paris.lutece.plugins.appcenter.business.ApplicationData;
import java.util.List;
import org.hibernate.validator.constraints.NotEmpty;


/**
 * This is the business class for the object SupportData
 */ 
public class SupportData extends ApplicationData
{

    // attributes
    @NotEmpty( message = "#i18n{module.appcenter.support.validation.questionSupport.notEmpty}" )
    private String _strQuestionSupport;
    private String _strResponseSupport;
    private List<Integer> _listIdFilesSupport;

    /**
     * Get the question for lutece support
     * @return the question for lutece support
     */
    public String getQuestionSupport( ) 
    {
        return _strQuestionSupport;
    }

    /**
     * Set the question for lutece support
     * @param strQuestionSupport
     */
    public void setQuestionSupport(String strQuestionSupport) 
    {
        this._strQuestionSupport = strQuestionSupport;
    }

    /**
     * Get the response of lutece support
     * @return the response for lutece support
     */
    public String getResponseSupport( ) 
    {
        return _strResponseSupport;
    }

    /**
     * Set the response of lutece support
     * @param strResponseSupport
     */
    public void setResponseSupport(String strResponseSupport) 
    {
        this._strResponseSupport = strResponseSupport;
    }


    /**
     * Get the list of file ids linked to the question for lutece support
     * @return the list of file ids linked to the question for lutece support
     */
    public List<Integer> getListIdFilesSupport( ) 
    {
            return _listIdFilesSupport;
    }

    /**
     * Set the list of file ids linked to the question for lutece support
     * @param _listIdFilesSupport
     */
    public void setListIdFilesSupport(List<Integer> _listIdFilesSupport) 
    {
            this._listIdFilesSupport = _listIdFilesSupport;
    }
}

