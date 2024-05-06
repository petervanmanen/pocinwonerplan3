import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Eroute from './eroute';
import ErouteDetail from './eroute-detail';
import ErouteUpdate from './eroute-update';
import ErouteDeleteDialog from './eroute-delete-dialog';

const ErouteRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Eroute />} />
    <Route path="new" element={<ErouteUpdate />} />
    <Route path=":id">
      <Route index element={<ErouteDetail />} />
      <Route path="edit" element={<ErouteUpdate />} />
      <Route path="delete" element={<ErouteDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default ErouteRoutes;
