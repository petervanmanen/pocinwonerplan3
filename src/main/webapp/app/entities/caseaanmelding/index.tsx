import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Caseaanmelding from './caseaanmelding';
import CaseaanmeldingDetail from './caseaanmelding-detail';
import CaseaanmeldingUpdate from './caseaanmelding-update';
import CaseaanmeldingDeleteDialog from './caseaanmelding-delete-dialog';

const CaseaanmeldingRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Caseaanmelding />} />
    <Route path="new" element={<CaseaanmeldingUpdate />} />
    <Route path=":id">
      <Route index element={<CaseaanmeldingDetail />} />
      <Route path="edit" element={<CaseaanmeldingUpdate />} />
      <Route path="delete" element={<CaseaanmeldingDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default CaseaanmeldingRoutes;
