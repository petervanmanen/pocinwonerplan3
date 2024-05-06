import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Overigbenoemdterrein from './overigbenoemdterrein';
import OverigbenoemdterreinDetail from './overigbenoemdterrein-detail';
import OverigbenoemdterreinUpdate from './overigbenoemdterrein-update';
import OverigbenoemdterreinDeleteDialog from './overigbenoemdterrein-delete-dialog';

const OverigbenoemdterreinRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Overigbenoemdterrein />} />
    <Route path="new" element={<OverigbenoemdterreinUpdate />} />
    <Route path=":id">
      <Route index element={<OverigbenoemdterreinDetail />} />
      <Route path="edit" element={<OverigbenoemdterreinUpdate />} />
      <Route path="delete" element={<OverigbenoemdterreinDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default OverigbenoemdterreinRoutes;
