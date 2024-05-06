import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Asielstatushouder from './asielstatushouder';
import AsielstatushouderDetail from './asielstatushouder-detail';
import AsielstatushouderUpdate from './asielstatushouder-update';
import AsielstatushouderDeleteDialog from './asielstatushouder-delete-dialog';

const AsielstatushouderRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Asielstatushouder />} />
    <Route path="new" element={<AsielstatushouderUpdate />} />
    <Route path=":id">
      <Route index element={<AsielstatushouderDetail />} />
      <Route path="edit" element={<AsielstatushouderUpdate />} />
      <Route path="delete" element={<AsielstatushouderDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default AsielstatushouderRoutes;
