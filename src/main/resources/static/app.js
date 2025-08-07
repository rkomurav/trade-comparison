/**
 * Angular application for Trade Document Comparison Tool
 */
angular.module('tradeComparisonApp', [])
    .controller('ComparisonController', ['$scope', '$http', function($scope, $http) {
        // Initialize variables
        $scope.agreementFolderPath = '';
        $scope.termSheetFolderPath = '';
        $scope.tradeAgreements = [];
        $scope.termSheets = [];
        $scope.selectedTradeAgreement = null;
        $scope.selectedTermSheet = null;
        $scope.comparisonResult = null;
        $scope.error = null;
        
        // API base URL
        const apiBaseUrl = '/api/documents';
        
        /**
         * Fetch available trade agreements from the specified folder
         */
        $scope.fetchTradeAgreements = function() {
            $scope.error = null;
            
            if (!$scope.agreementFolderPath) {
                $scope.error = 'Please enter a folder path for trade agreements';
                return;
            }
            
            $http.get(`${apiBaseUrl}/trade-agreements`, {
                params: { folderPath: $scope.agreementFolderPath }
            }).then(function(response) {
                $scope.tradeAgreements = response.data;
                if ($scope.tradeAgreements.length === 0) {
                    $scope.error = 'No trade agreements found in the specified folder';
                }
            }).catch(function(error) {
                $scope.error = error.data?.error || 'Failed to fetch trade agreements';
                console.error('Error fetching trade agreements:', error);
            });
        };
        
        /**
         * Fetch available term sheets from the specified folder
         */
        $scope.fetchTermSheets = function() {
            $scope.error = null;
            
            if (!$scope.termSheetFolderPath) {
                $scope.error = 'Please enter a folder path for term sheets';
                return;
            }
            
            $http.get(`${apiBaseUrl}/term-sheets`, {
                params: { folderPath: $scope.termSheetFolderPath }
            }).then(function(response) {
                $scope.termSheets = response.data;
                if ($scope.termSheets.length === 0) {
                    $scope.error = 'No term sheets found in the specified folder';
                }
            }).catch(function(error) {
                $scope.error = error.data?.error || 'Failed to fetch term sheets';
                console.error('Error fetching term sheets:', error);
            });
        };
        
        /**
         * Compare the selected trade agreement and term sheet
         */
        $scope.compareDocuments = function() {
            $scope.error = null;
            $scope.comparisonResult = null;
            
            if (!$scope.selectedTradeAgreement || !$scope.selectedTermSheet) {
                $scope.error = 'Please select both a trade agreement and a term sheet';
                return;
            }
            
            $http.get(`${apiBaseUrl}/compare`, {
                params: {
                    tradeAgreementPath: $scope.selectedTradeAgreement,
                    termSheetPath: $scope.selectedTermSheet
                }
            }).then(function(response) {
                // Parse the JSON string if needed
                if (typeof response.data === 'string') {
                    $scope.comparisonResult = JSON.parse(response.data);
                } else {
                    $scope.comparisonResult = response.data;
                }
            }).catch(function(error) {
                $scope.error = error.data?.error || 'Failed to compare documents';
                console.error('Error comparing documents:', error);
            });
        };
        
        /**
         * Extract file name from full path
         */
        $scope.getFileName = function(filePath) {
            if (!filePath) return '';
            const parts = filePath.split('\\');
            return parts[parts.length - 1];
        };
        
        /**
         * Format field name for display (convert camelCase to Title Case)
         */
        $scope.formatFieldName = function(fieldName) {
            if (!fieldName) return '';
            
            // Convert camelCase to space-separated words
            const spacedName = fieldName.replace(/([A-Z])/g, ' $1').trim();
            
            // Capitalize first letter of each word
            return spacedName.replace(/\w\S*/g, function(txt) {
                return txt.charAt(0).toUpperCase() + txt.substr(1).toLowerCase();
            });
        };
    }]);