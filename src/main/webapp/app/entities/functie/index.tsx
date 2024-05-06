import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Functie from './functie';
import FunctieDetail from './functie-detail';
import FunctieUpdate from './functie-update';
import FunctieDeleteDialog from './functie-delete-dialog';

const FunctieRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Functie />} />
    <Route path="new" element={<FunctieUpdate />} />
    <Route path=":id">
      <Route index element={<FunctieDetail />} />
      <Route path="edit" element={<FunctieUpdate />} />
      <Route path="delete" element={<FunctieDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default FunctieRoutes;
