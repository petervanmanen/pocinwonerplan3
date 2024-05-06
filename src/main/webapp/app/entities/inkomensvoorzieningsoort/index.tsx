import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Inkomensvoorzieningsoort from './inkomensvoorzieningsoort';
import InkomensvoorzieningsoortDetail from './inkomensvoorzieningsoort-detail';
import InkomensvoorzieningsoortUpdate from './inkomensvoorzieningsoort-update';
import InkomensvoorzieningsoortDeleteDialog from './inkomensvoorzieningsoort-delete-dialog';

const InkomensvoorzieningsoortRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Inkomensvoorzieningsoort />} />
    <Route path="new" element={<InkomensvoorzieningsoortUpdate />} />
    <Route path=":id">
      <Route index element={<InkomensvoorzieningsoortDetail />} />
      <Route path="edit" element={<InkomensvoorzieningsoortUpdate />} />
      <Route path="delete" element={<InkomensvoorzieningsoortDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default InkomensvoorzieningsoortRoutes;
