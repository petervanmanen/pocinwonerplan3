import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Meldingongeval from './meldingongeval';
import MeldingongevalDetail from './meldingongeval-detail';
import MeldingongevalUpdate from './meldingongeval-update';
import MeldingongevalDeleteDialog from './meldingongeval-delete-dialog';

const MeldingongevalRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Meldingongeval />} />
    <Route path="new" element={<MeldingongevalUpdate />} />
    <Route path=":id">
      <Route index element={<MeldingongevalDetail />} />
      <Route path="edit" element={<MeldingongevalUpdate />} />
      <Route path="delete" element={<MeldingongevalDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default MeldingongevalRoutes;
