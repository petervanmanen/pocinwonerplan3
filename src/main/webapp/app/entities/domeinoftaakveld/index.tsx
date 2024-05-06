import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Domeinoftaakveld from './domeinoftaakveld';
import DomeinoftaakveldDetail from './domeinoftaakveld-detail';
import DomeinoftaakveldUpdate from './domeinoftaakveld-update';
import DomeinoftaakveldDeleteDialog from './domeinoftaakveld-delete-dialog';

const DomeinoftaakveldRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Domeinoftaakveld />} />
    <Route path="new" element={<DomeinoftaakveldUpdate />} />
    <Route path=":id">
      <Route index element={<DomeinoftaakveldDetail />} />
      <Route path="edit" element={<DomeinoftaakveldUpdate />} />
      <Route path="delete" element={<DomeinoftaakveldDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default DomeinoftaakveldRoutes;
