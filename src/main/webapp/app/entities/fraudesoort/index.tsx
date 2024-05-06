import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Fraudesoort from './fraudesoort';
import FraudesoortDetail from './fraudesoort-detail';
import FraudesoortUpdate from './fraudesoort-update';
import FraudesoortDeleteDialog from './fraudesoort-delete-dialog';

const FraudesoortRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Fraudesoort />} />
    <Route path="new" element={<FraudesoortUpdate />} />
    <Route path=":id">
      <Route index element={<FraudesoortDetail />} />
      <Route path="edit" element={<FraudesoortUpdate />} />
      <Route path="delete" element={<FraudesoortDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default FraudesoortRoutes;
