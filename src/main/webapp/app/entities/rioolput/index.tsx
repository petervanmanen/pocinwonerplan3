import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Rioolput from './rioolput';
import RioolputDetail from './rioolput-detail';
import RioolputUpdate from './rioolput-update';
import RioolputDeleteDialog from './rioolput-delete-dialog';

const RioolputRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Rioolput />} />
    <Route path="new" element={<RioolputUpdate />} />
    <Route path=":id">
      <Route index element={<RioolputDetail />} />
      <Route path="edit" element={<RioolputUpdate />} />
      <Route path="delete" element={<RioolputDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default RioolputRoutes;
