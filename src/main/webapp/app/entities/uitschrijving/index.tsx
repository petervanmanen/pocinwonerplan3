import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Uitschrijving from './uitschrijving';
import UitschrijvingDetail from './uitschrijving-detail';
import UitschrijvingUpdate from './uitschrijving-update';
import UitschrijvingDeleteDialog from './uitschrijving-delete-dialog';

const UitschrijvingRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Uitschrijving />} />
    <Route path="new" element={<UitschrijvingUpdate />} />
    <Route path=":id">
      <Route index element={<UitschrijvingDetail />} />
      <Route path="edit" element={<UitschrijvingUpdate />} />
      <Route path="delete" element={<UitschrijvingDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default UitschrijvingRoutes;
