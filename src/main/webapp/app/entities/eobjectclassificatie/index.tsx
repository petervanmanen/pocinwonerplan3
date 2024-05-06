import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Eobjectclassificatie from './eobjectclassificatie';
import EobjectclassificatieDetail from './eobjectclassificatie-detail';
import EobjectclassificatieUpdate from './eobjectclassificatie-update';
import EobjectclassificatieDeleteDialog from './eobjectclassificatie-delete-dialog';

const EobjectclassificatieRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Eobjectclassificatie />} />
    <Route path="new" element={<EobjectclassificatieUpdate />} />
    <Route path=":id">
      <Route index element={<EobjectclassificatieDetail />} />
      <Route path="edit" element={<EobjectclassificatieUpdate />} />
      <Route path="delete" element={<EobjectclassificatieDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default EobjectclassificatieRoutes;
