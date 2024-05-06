import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Procesverbaalmoormelding from './procesverbaalmoormelding';
import ProcesverbaalmoormeldingDetail from './procesverbaalmoormelding-detail';
import ProcesverbaalmoormeldingUpdate from './procesverbaalmoormelding-update';
import ProcesverbaalmoormeldingDeleteDialog from './procesverbaalmoormelding-delete-dialog';

const ProcesverbaalmoormeldingRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Procesverbaalmoormelding />} />
    <Route path="new" element={<ProcesverbaalmoormeldingUpdate />} />
    <Route path=":id">
      <Route index element={<ProcesverbaalmoormeldingDetail />} />
      <Route path="edit" element={<ProcesverbaalmoormeldingUpdate />} />
      <Route path="delete" element={<ProcesverbaalmoormeldingDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default ProcesverbaalmoormeldingRoutes;
