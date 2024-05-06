import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Ecomponentsoort from './ecomponentsoort';
import EcomponentsoortDetail from './ecomponentsoort-detail';
import EcomponentsoortUpdate from './ecomponentsoort-update';
import EcomponentsoortDeleteDialog from './ecomponentsoort-delete-dialog';

const EcomponentsoortRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Ecomponentsoort />} />
    <Route path="new" element={<EcomponentsoortUpdate />} />
    <Route path=":id">
      <Route index element={<EcomponentsoortDetail />} />
      <Route path="edit" element={<EcomponentsoortUpdate />} />
      <Route path="delete" element={<EcomponentsoortDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default EcomponentsoortRoutes;
