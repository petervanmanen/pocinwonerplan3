import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Boom from './boom';
import BoomDetail from './boom-detail';
import BoomUpdate from './boom-update';
import BoomDeleteDialog from './boom-delete-dialog';

const BoomRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Boom />} />
    <Route path="new" element={<BoomUpdate />} />
    <Route path=":id">
      <Route index element={<BoomDetail />} />
      <Route path="edit" element={<BoomUpdate />} />
      <Route path="delete" element={<BoomDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default BoomRoutes;
