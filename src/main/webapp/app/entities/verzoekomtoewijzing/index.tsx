import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Verzoekomtoewijzing from './verzoekomtoewijzing';
import VerzoekomtoewijzingDetail from './verzoekomtoewijzing-detail';
import VerzoekomtoewijzingUpdate from './verzoekomtoewijzing-update';
import VerzoekomtoewijzingDeleteDialog from './verzoekomtoewijzing-delete-dialog';

const VerzoekomtoewijzingRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Verzoekomtoewijzing />} />
    <Route path="new" element={<VerzoekomtoewijzingUpdate />} />
    <Route path=":id">
      <Route index element={<VerzoekomtoewijzingDetail />} />
      <Route path="edit" element={<VerzoekomtoewijzingUpdate />} />
      <Route path="delete" element={<VerzoekomtoewijzingDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default VerzoekomtoewijzingRoutes;
