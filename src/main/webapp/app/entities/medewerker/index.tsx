import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Medewerker from './medewerker';
import MedewerkerDetail from './medewerker-detail';
import MedewerkerUpdate from './medewerker-update';
import MedewerkerDeleteDialog from './medewerker-delete-dialog';

const MedewerkerRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Medewerker />} />
    <Route path="new" element={<MedewerkerUpdate />} />
    <Route path=":id">
      <Route index element={<MedewerkerDetail />} />
      <Route path="edit" element={<MedewerkerUpdate />} />
      <Route path="delete" element={<MedewerkerDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default MedewerkerRoutes;
