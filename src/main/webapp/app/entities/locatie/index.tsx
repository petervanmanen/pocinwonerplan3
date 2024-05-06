import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Locatie from './locatie';
import LocatieDetail from './locatie-detail';
import LocatieUpdate from './locatie-update';
import LocatieDeleteDialog from './locatie-delete-dialog';

const LocatieRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Locatie />} />
    <Route path="new" element={<LocatieUpdate />} />
    <Route path=":id">
      <Route index element={<LocatieDetail />} />
      <Route path="edit" element={<LocatieUpdate />} />
      <Route path="delete" element={<LocatieDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default LocatieRoutes;
