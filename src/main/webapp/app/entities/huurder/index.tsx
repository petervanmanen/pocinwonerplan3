import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Huurder from './huurder';
import HuurderDetail from './huurder-detail';
import HuurderUpdate from './huurder-update';
import HuurderDeleteDialog from './huurder-delete-dialog';

const HuurderRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Huurder />} />
    <Route path="new" element={<HuurderUpdate />} />
    <Route path=":id">
      <Route index element={<HuurderDetail />} />
      <Route path="edit" element={<HuurderUpdate />} />
      <Route path="delete" element={<HuurderDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default HuurderRoutes;
