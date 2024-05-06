import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Rekeningnummer from './rekeningnummer';
import RekeningnummerDetail from './rekeningnummer-detail';
import RekeningnummerUpdate from './rekeningnummer-update';
import RekeningnummerDeleteDialog from './rekeningnummer-delete-dialog';

const RekeningnummerRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Rekeningnummer />} />
    <Route path="new" element={<RekeningnummerUpdate />} />
    <Route path=":id">
      <Route index element={<RekeningnummerDetail />} />
      <Route path="edit" element={<RekeningnummerUpdate />} />
      <Route path="delete" element={<RekeningnummerDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default RekeningnummerRoutes;
