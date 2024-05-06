import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Werkorder from './werkorder';
import WerkorderDetail from './werkorder-detail';
import WerkorderUpdate from './werkorder-update';
import WerkorderDeleteDialog from './werkorder-delete-dialog';

const WerkorderRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Werkorder />} />
    <Route path="new" element={<WerkorderUpdate />} />
    <Route path=":id">
      <Route index element={<WerkorderDetail />} />
      <Route path="edit" element={<WerkorderUpdate />} />
      <Route path="delete" element={<WerkorderDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default WerkorderRoutes;
