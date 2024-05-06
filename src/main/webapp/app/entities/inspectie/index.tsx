import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Inspectie from './inspectie';
import InspectieDetail from './inspectie-detail';
import InspectieUpdate from './inspectie-update';
import InspectieDeleteDialog from './inspectie-delete-dialog';

const InspectieRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Inspectie />} />
    <Route path="new" element={<InspectieUpdate />} />
    <Route path=":id">
      <Route index element={<InspectieDetail />} />
      <Route path="edit" element={<InspectieUpdate />} />
      <Route path="delete" element={<InspectieDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default InspectieRoutes;
