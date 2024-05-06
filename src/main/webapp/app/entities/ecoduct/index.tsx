import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Ecoduct from './ecoduct';
import EcoductDetail from './ecoduct-detail';
import EcoductUpdate from './ecoduct-update';
import EcoductDeleteDialog from './ecoduct-delete-dialog';

const EcoductRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Ecoduct />} />
    <Route path="new" element={<EcoductUpdate />} />
    <Route path=":id">
      <Route index element={<EcoductDetail />} />
      <Route path="edit" element={<EcoductUpdate />} />
      <Route path="delete" element={<EcoductDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default EcoductRoutes;
