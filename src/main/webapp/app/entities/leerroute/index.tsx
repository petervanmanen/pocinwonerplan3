import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Leerroute from './leerroute';
import LeerrouteDetail from './leerroute-detail';
import LeerrouteUpdate from './leerroute-update';
import LeerrouteDeleteDialog from './leerroute-delete-dialog';

const LeerrouteRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Leerroute />} />
    <Route path="new" element={<LeerrouteUpdate />} />
    <Route path=":id">
      <Route index element={<LeerrouteDetail />} />
      <Route path="edit" element={<LeerrouteUpdate />} />
      <Route path="delete" element={<LeerrouteDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default LeerrouteRoutes;
