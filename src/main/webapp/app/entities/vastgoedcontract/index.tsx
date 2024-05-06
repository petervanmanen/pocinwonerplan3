import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Vastgoedcontract from './vastgoedcontract';
import VastgoedcontractDetail from './vastgoedcontract-detail';
import VastgoedcontractUpdate from './vastgoedcontract-update';
import VastgoedcontractDeleteDialog from './vastgoedcontract-delete-dialog';

const VastgoedcontractRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Vastgoedcontract />} />
    <Route path="new" element={<VastgoedcontractUpdate />} />
    <Route path=":id">
      <Route index element={<VastgoedcontractDetail />} />
      <Route path="edit" element={<VastgoedcontractUpdate />} />
      <Route path="delete" element={<VastgoedcontractDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default VastgoedcontractRoutes;
