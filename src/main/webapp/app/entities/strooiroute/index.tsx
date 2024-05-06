import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Strooiroute from './strooiroute';
import StrooirouteDetail from './strooiroute-detail';
import StrooirouteUpdate from './strooiroute-update';
import StrooirouteDeleteDialog from './strooiroute-delete-dialog';

const StrooirouteRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Strooiroute />} />
    <Route path="new" element={<StrooirouteUpdate />} />
    <Route path=":id">
      <Route index element={<StrooirouteDetail />} />
      <Route path="edit" element={<StrooirouteUpdate />} />
      <Route path="delete" element={<StrooirouteDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default StrooirouteRoutes;
