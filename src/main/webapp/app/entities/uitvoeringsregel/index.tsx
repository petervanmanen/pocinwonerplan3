import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Uitvoeringsregel from './uitvoeringsregel';
import UitvoeringsregelDetail from './uitvoeringsregel-detail';
import UitvoeringsregelUpdate from './uitvoeringsregel-update';
import UitvoeringsregelDeleteDialog from './uitvoeringsregel-delete-dialog';

const UitvoeringsregelRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Uitvoeringsregel />} />
    <Route path="new" element={<UitvoeringsregelUpdate />} />
    <Route path=":id">
      <Route index element={<UitvoeringsregelDetail />} />
      <Route path="edit" element={<UitvoeringsregelUpdate />} />
      <Route path="delete" element={<UitvoeringsregelDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default UitvoeringsregelRoutes;
