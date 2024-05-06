import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Binnenlocatie from './binnenlocatie';
import BinnenlocatieDetail from './binnenlocatie-detail';
import BinnenlocatieUpdate from './binnenlocatie-update';
import BinnenlocatieDeleteDialog from './binnenlocatie-delete-dialog';

const BinnenlocatieRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Binnenlocatie />} />
    <Route path="new" element={<BinnenlocatieUpdate />} />
    <Route path=":id">
      <Route index element={<BinnenlocatieDetail />} />
      <Route path="edit" element={<BinnenlocatieUpdate />} />
      <Route path="delete" element={<BinnenlocatieDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default BinnenlocatieRoutes;
