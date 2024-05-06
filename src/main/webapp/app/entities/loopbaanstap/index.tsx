import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Loopbaanstap from './loopbaanstap';
import LoopbaanstapDetail from './loopbaanstap-detail';
import LoopbaanstapUpdate from './loopbaanstap-update';
import LoopbaanstapDeleteDialog from './loopbaanstap-delete-dialog';

const LoopbaanstapRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Loopbaanstap />} />
    <Route path="new" element={<LoopbaanstapUpdate />} />
    <Route path=":id">
      <Route index element={<LoopbaanstapDetail />} />
      <Route path="edit" element={<LoopbaanstapUpdate />} />
      <Route path="delete" element={<LoopbaanstapDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default LoopbaanstapRoutes;
