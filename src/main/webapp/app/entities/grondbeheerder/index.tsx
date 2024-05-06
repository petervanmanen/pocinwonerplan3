import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Grondbeheerder from './grondbeheerder';
import GrondbeheerderDetail from './grondbeheerder-detail';
import GrondbeheerderUpdate from './grondbeheerder-update';
import GrondbeheerderDeleteDialog from './grondbeheerder-delete-dialog';

const GrondbeheerderRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Grondbeheerder />} />
    <Route path="new" element={<GrondbeheerderUpdate />} />
    <Route path=":id">
      <Route index element={<GrondbeheerderDetail />} />
      <Route path="edit" element={<GrondbeheerderUpdate />} />
      <Route path="delete" element={<GrondbeheerderDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default GrondbeheerderRoutes;
