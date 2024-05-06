import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Indiener from './indiener';
import IndienerDetail from './indiener-detail';
import IndienerUpdate from './indiener-update';
import IndienerDeleteDialog from './indiener-delete-dialog';

const IndienerRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Indiener />} />
    <Route path="new" element={<IndienerUpdate />} />
    <Route path=":id">
      <Route index element={<IndienerDetail />} />
      <Route path="edit" element={<IndienerUpdate />} />
      <Route path="delete" element={<IndienerDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default IndienerRoutes;
