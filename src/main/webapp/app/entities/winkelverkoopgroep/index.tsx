import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Winkelverkoopgroep from './winkelverkoopgroep';
import WinkelverkoopgroepDetail from './winkelverkoopgroep-detail';
import WinkelverkoopgroepUpdate from './winkelverkoopgroep-update';
import WinkelverkoopgroepDeleteDialog from './winkelverkoopgroep-delete-dialog';

const WinkelverkoopgroepRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Winkelverkoopgroep />} />
    <Route path="new" element={<WinkelverkoopgroepUpdate />} />
    <Route path=":id">
      <Route index element={<WinkelverkoopgroepDetail />} />
      <Route path="edit" element={<WinkelverkoopgroepUpdate />} />
      <Route path="delete" element={<WinkelverkoopgroepDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default WinkelverkoopgroepRoutes;
