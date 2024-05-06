import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Winkelvoorraaditem from './winkelvoorraaditem';
import WinkelvoorraaditemDetail from './winkelvoorraaditem-detail';
import WinkelvoorraaditemUpdate from './winkelvoorraaditem-update';
import WinkelvoorraaditemDeleteDialog from './winkelvoorraaditem-delete-dialog';

const WinkelvoorraaditemRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Winkelvoorraaditem />} />
    <Route path="new" element={<WinkelvoorraaditemUpdate />} />
    <Route path=":id">
      <Route index element={<WinkelvoorraaditemDetail />} />
      <Route path="edit" element={<WinkelvoorraaditemUpdate />} />
      <Route path="delete" element={<WinkelvoorraaditemDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default WinkelvoorraaditemRoutes;
