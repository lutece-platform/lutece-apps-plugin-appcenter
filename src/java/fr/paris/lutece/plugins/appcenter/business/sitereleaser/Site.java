package fr.paris.lutece.plugins.appcenter.business.sitereleaser;

public class Site {

	private String _strArtifactId;
    private int _nIdCluster;
    private String _strScmUrl;
    private String _strName;
    private String _strDescription;
    private String _strJiraKey;
    private boolean _bTheme;

    /**
     * Returns the ArtifactId.
     *
     * @return The ArtifactId
     */
    public String getArtifactId( )
    {
        return _strArtifactId;
    }

    /**
     * Sets the ArtifactId.
     *
     * @param strArtifactId
     *            The ArtifactId
     */
    public void setArtifactId( String strArtifactId )
    {
        _strArtifactId = strArtifactId;
    }

    /**
     * Returns the IdCluster.
     *
     * @return The IdCluster
     */
    public int getIdCluster( )
    {
        return _nIdCluster;
    }

    /**
     * Sets the IdCluster.
     *
     * @param nIdCluster
     *            The IdCluster
     */
    public void setIdCluster( int nIdCluster )
    {
        _nIdCluster = nIdCluster;
    }

    /**
     * Returns the ScmUrl.
     *
     * @return The ScmUrl
     */
    public String getScmUrl( )
    {
        return _strScmUrl;
    }

    /**
     * Sets the ScmUrl.
     *
     * @param strScmUrl
     *            The ScmUrl
     */
    public void setScmUrl( String strScmUrl )
    {
        _strScmUrl = strScmUrl;
    }

    /**
     * Returns the Name.
     *
     * @return The Name
     */
    public String getName( )
    {
        return _strName;
    }

    /**
     * Sets the Name.
     *
     * @param strName
     *            The Name
     */
    public void setName( String strName )
    {
        _strName = strName;
    }

    /**
     * Returns the Description.
     *
     * @return The Description
     */
    public String getDescription( )
    {
        return _strDescription;
    }

    /**
     * Sets the Description.
     *
     * @param strDescription
     *            The Description
     */
    public void setDescription( String strDescription )
    {
        _strDescription = strDescription;
    }

    /**
     * Returns the JiraKey.
     *
     * @return The JiraKey
     */
    public String getJiraKey( )
    {
        return _strJiraKey;
    }

    /**
     * Sets the JiraKey.
     *
     * @param strJiraKey
     *            The JiraKey
     */
    public void setJiraKey( String strJiraKey )
    {
        _strJiraKey = strJiraKey;
    }

    /**
     * Checks if is theme.
     *
     * @return isTheme
     */
    public boolean isTheme( )
    {
        return _bTheme;
    }

    /**
     * Sets the theme.
     *
     * @param _bTheme
     *            the new theme
     */
    public void setTheme( boolean _bTheme )
    {
        this._bTheme = _bTheme;
    }


}
