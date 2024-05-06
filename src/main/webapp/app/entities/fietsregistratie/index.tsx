import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Fietsregistratie from './fietsregistratie';
import FietsregistratieDetail from './fietsregistratie-detail';
import FietsregistratieUpdate from './fietsregistratie-update';
import FietsregistratieDeleteDialog from './fietsregistratie-delete-dialog';

const FietsregistratieRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Fietsregistratie />} />
    <Route path="new" element={<FietsregistratieUpdate />} />
    <Route path=":id">
      <Route index element={<FietsregistratieDetail />} />
      <Route path="edit" element={<FietsregistratieUpdate />} />
      <Route path="delete" element={<FietsregistratieDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default FietsregistratieRoutes;
