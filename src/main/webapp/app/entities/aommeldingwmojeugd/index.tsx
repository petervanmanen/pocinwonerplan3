import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Aommeldingwmojeugd from './aommeldingwmojeugd';
import AommeldingwmojeugdDetail from './aommeldingwmojeugd-detail';
import AommeldingwmojeugdUpdate from './aommeldingwmojeugd-update';
import AommeldingwmojeugdDeleteDialog from './aommeldingwmojeugd-delete-dialog';

const AommeldingwmojeugdRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Aommeldingwmojeugd />} />
    <Route path="new" element={<AommeldingwmojeugdUpdate />} />
    <Route path=":id">
      <Route index element={<AommeldingwmojeugdDetail />} />
      <Route path="edit" element={<AommeldingwmojeugdUpdate />} />
      <Route path="delete" element={<AommeldingwmojeugdDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default AommeldingwmojeugdRoutes;
