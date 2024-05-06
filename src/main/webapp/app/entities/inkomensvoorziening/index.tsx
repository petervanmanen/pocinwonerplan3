import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Inkomensvoorziening from './inkomensvoorziening';
import InkomensvoorzieningDetail from './inkomensvoorziening-detail';
import InkomensvoorzieningUpdate from './inkomensvoorziening-update';
import InkomensvoorzieningDeleteDialog from './inkomensvoorziening-delete-dialog';

const InkomensvoorzieningRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Inkomensvoorziening />} />
    <Route path="new" element={<InkomensvoorzieningUpdate />} />
    <Route path=":id">
      <Route index element={<InkomensvoorzieningDetail />} />
      <Route path="edit" element={<InkomensvoorzieningUpdate />} />
      <Route path="delete" element={<InkomensvoorzieningDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default InkomensvoorzieningRoutes;
