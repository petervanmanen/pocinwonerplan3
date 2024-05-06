import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Magazijnlocatie from './magazijnlocatie';
import MagazijnlocatieDetail from './magazijnlocatie-detail';
import MagazijnlocatieUpdate from './magazijnlocatie-update';
import MagazijnlocatieDeleteDialog from './magazijnlocatie-delete-dialog';

const MagazijnlocatieRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Magazijnlocatie />} />
    <Route path="new" element={<MagazijnlocatieUpdate />} />
    <Route path=":id">
      <Route index element={<MagazijnlocatieDetail />} />
      <Route path="edit" element={<MagazijnlocatieUpdate />} />
      <Route path="delete" element={<MagazijnlocatieDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default MagazijnlocatieRoutes;
