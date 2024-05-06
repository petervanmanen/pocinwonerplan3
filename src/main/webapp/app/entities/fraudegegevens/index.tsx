import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Fraudegegevens from './fraudegegevens';
import FraudegegevensDetail from './fraudegegevens-detail';
import FraudegegevensUpdate from './fraudegegevens-update';
import FraudegegevensDeleteDialog from './fraudegegevens-delete-dialog';

const FraudegegevensRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Fraudegegevens />} />
    <Route path="new" element={<FraudegegevensUpdate />} />
    <Route path=":id">
      <Route index element={<FraudegegevensDetail />} />
      <Route path="edit" element={<FraudegegevensUpdate />} />
      <Route path="delete" element={<FraudegegevensDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default FraudegegevensRoutes;
