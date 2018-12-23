############################################################
# This is the TirNaNog install script                      #
#                                                          #
# dependencies will be installed and configured and        #
# the project will be set to auto start on startup         #
# Make sure to run this script as sudo.                    #
# Do not change any default prompts, accept default values #
# You can run this script by calling the following:        #
# sudo sh install.sh                                       #
############################################################

apt-get install mysql-server
mysql -u root < InitialDatabaseSetup.sql
