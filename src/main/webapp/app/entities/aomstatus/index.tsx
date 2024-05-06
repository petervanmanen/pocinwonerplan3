import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Aomstatus from './aomstatus';
import AomstatusDetail from './aomstatus-detail';
import AomstatusUpdate from './aomstatus-update';
import AomstatusDeleteDialog from './aomstatus-delete-dialog';

const AomstatusRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Aomstatus />} />
    <Route path="new" element={<AomstatusUpdate />} />
    <Route path=":id">
      <Route index element={<AomstatusDetail />} />
      <Route path="edit" element={<AomstatusUpdate />} />
      <Route path="delete" element={<AomstatusDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default AomstatusRoutes;
