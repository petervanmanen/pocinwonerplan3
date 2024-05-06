import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Kadastralemutatie from './kadastralemutatie';
import KadastralemutatieDetail from './kadastralemutatie-detail';
import KadastralemutatieUpdate from './kadastralemutatie-update';
import KadastralemutatieDeleteDialog from './kadastralemutatie-delete-dialog';

const KadastralemutatieRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Kadastralemutatie />} />
    <Route path="new" element={<KadastralemutatieUpdate />} />
    <Route path=":id">
      <Route index element={<KadastralemutatieDetail />} />
      <Route path="edit" element={<KadastralemutatieUpdate />} />
      <Route path="delete" element={<KadastralemutatieDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default KadastralemutatieRoutes;
