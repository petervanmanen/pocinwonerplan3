import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Museumrelatie from './museumrelatie';
import MuseumrelatieDetail from './museumrelatie-detail';
import MuseumrelatieUpdate from './museumrelatie-update';
import MuseumrelatieDeleteDialog from './museumrelatie-delete-dialog';

const MuseumrelatieRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Museumrelatie />} />
    <Route path="new" element={<MuseumrelatieUpdate />} />
    <Route path=":id">
      <Route index element={<MuseumrelatieDetail />} />
      <Route path="edit" element={<MuseumrelatieUpdate />} />
      <Route path="delete" element={<MuseumrelatieDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default MuseumrelatieRoutes;
