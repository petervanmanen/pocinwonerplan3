import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Participatiedossier from './participatiedossier';
import ParticipatiedossierDetail from './participatiedossier-detail';
import ParticipatiedossierUpdate from './participatiedossier-update';
import ParticipatiedossierDeleteDialog from './participatiedossier-delete-dialog';

const ParticipatiedossierRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Participatiedossier />} />
    <Route path="new" element={<ParticipatiedossierUpdate />} />
    <Route path=":id">
      <Route index element={<ParticipatiedossierDetail />} />
      <Route path="edit" element={<ParticipatiedossierUpdate />} />
      <Route path="delete" element={<ParticipatiedossierDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default ParticipatiedossierRoutes;
