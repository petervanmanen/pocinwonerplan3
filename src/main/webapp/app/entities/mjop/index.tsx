import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Mjop from './mjop';
import MjopDetail from './mjop-detail';
import MjopUpdate from './mjop-update';
import MjopDeleteDialog from './mjop-delete-dialog';

const MjopRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Mjop />} />
    <Route path="new" element={<MjopUpdate />} />
    <Route path=":id">
      <Route index element={<MjopDetail />} />
      <Route path="edit" element={<MjopUpdate />} />
      <Route path="delete" element={<MjopDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default MjopRoutes;
