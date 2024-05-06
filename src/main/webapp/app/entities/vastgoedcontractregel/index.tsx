import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Vastgoedcontractregel from './vastgoedcontractregel';
import VastgoedcontractregelDetail from './vastgoedcontractregel-detail';
import VastgoedcontractregelUpdate from './vastgoedcontractregel-update';
import VastgoedcontractregelDeleteDialog from './vastgoedcontractregel-delete-dialog';

const VastgoedcontractregelRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Vastgoedcontractregel />} />
    <Route path="new" element={<VastgoedcontractregelUpdate />} />
    <Route path=":id">
      <Route index element={<VastgoedcontractregelDetail />} />
      <Route path="edit" element={<VastgoedcontractregelUpdate />} />
      <Route path="delete" element={<VastgoedcontractregelDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default VastgoedcontractregelRoutes;
