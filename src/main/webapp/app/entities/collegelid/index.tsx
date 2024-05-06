import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Collegelid from './collegelid';
import CollegelidDetail from './collegelid-detail';
import CollegelidUpdate from './collegelid-update';
import CollegelidDeleteDialog from './collegelid-delete-dialog';

const CollegelidRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Collegelid />} />
    <Route path="new" element={<CollegelidUpdate />} />
    <Route path=":id">
      <Route index element={<CollegelidDetail />} />
      <Route path="edit" element={<CollegelidUpdate />} />
      <Route path="delete" element={<CollegelidDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default CollegelidRoutes;
