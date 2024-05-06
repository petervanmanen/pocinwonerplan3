import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Inschrijving from './inschrijving';
import InschrijvingDetail from './inschrijving-detail';
import InschrijvingUpdate from './inschrijving-update';
import InschrijvingDeleteDialog from './inschrijving-delete-dialog';

const InschrijvingRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Inschrijving />} />
    <Route path="new" element={<InschrijvingUpdate />} />
    <Route path=":id">
      <Route index element={<InschrijvingDetail />} />
      <Route path="edit" element={<InschrijvingUpdate />} />
      <Route path="delete" element={<InschrijvingDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default InschrijvingRoutes;
