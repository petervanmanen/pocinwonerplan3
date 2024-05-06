import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Strooidag from './strooidag';
import StrooidagDetail from './strooidag-detail';
import StrooidagUpdate from './strooidag-update';
import StrooidagDeleteDialog from './strooidag-delete-dialog';

const StrooidagRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Strooidag />} />
    <Route path="new" element={<StrooidagUpdate />} />
    <Route path=":id">
      <Route index element={<StrooidagDetail />} />
      <Route path="edit" element={<StrooidagUpdate />} />
      <Route path="delete" element={<StrooidagDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default StrooidagRoutes;
