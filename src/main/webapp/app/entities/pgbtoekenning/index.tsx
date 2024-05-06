import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Pgbtoekenning from './pgbtoekenning';
import PgbtoekenningDetail from './pgbtoekenning-detail';
import PgbtoekenningUpdate from './pgbtoekenning-update';
import PgbtoekenningDeleteDialog from './pgbtoekenning-delete-dialog';

const PgbtoekenningRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Pgbtoekenning />} />
    <Route path="new" element={<PgbtoekenningUpdate />} />
    <Route path=":id">
      <Route index element={<PgbtoekenningDetail />} />
      <Route path="edit" element={<PgbtoekenningUpdate />} />
      <Route path="delete" element={<PgbtoekenningDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default PgbtoekenningRoutes;
